Public Class Form1

    Dim Op As String
    Dim num1 As Double
    Dim num2 As Double

    Private Sub Form1_Load(sender As Object, e As EventArgs) Handles MyBase.Load
        Op = ""
        num1 = 0
        num2 = 0
    End Sub

    Private Sub Button26_Click(sender As Object, e As EventArgs) Handles Button26.Click
        TextBox1.Text = TextBox1.Text & "1"
    End Sub

    Private Sub Button30_Click(sender As Object, e As EventArgs) Handles Button30.Click
        TextBox1.Text = TextBox1.Text & "0"
    End Sub

    Private Sub Button27_Click(sender As Object, e As EventArgs) Handles Button27.Click
        TextBox1.Text = TextBox1.Text & "2"
    End Sub

    Private Sub Button28_Click(sender As Object, e As EventArgs) Handles Button28.Click
        TextBox1.Text = TextBox1.Text & "3"
    End Sub

    Private Sub Button21_Click(sender As Object, e As EventArgs) Handles Button21.Click
        TextBox1.Text = TextBox1.Text & "4"
    End Sub

    Private Sub Button22_Click(sender As Object, e As EventArgs) Handles Button22.Click
        TextBox1.Text = TextBox1.Text & "5"
    End Sub

    Private Sub Button23_Click(sender As Object, e As EventArgs) Handles Button23.Click
        TextBox1.Text = TextBox1.Text & "6"
    End Sub

    Private Sub Button16_Click(sender As Object, e As EventArgs) Handles Button16.Click
        TextBox1.Text = TextBox1.Text & "7"
    End Sub

    Private Sub Button17_Click(sender As Object, e As EventArgs) Handles Button17.Click
        TextBox1.Text = TextBox1.Text & "8"
    End Sub

    Private Sub Button18_Click(sender As Object, e As EventArgs) Handles Button18.Click
        TextBox1.Text = TextBox1.Text & "9"
    End Sub

    Private Sub Button31_Click(sender As Object, e As EventArgs) Handles Button31.Click
        TextBox1.Text = TextBox1.Text & "."
    End Sub


    Private Sub Button20_Click(sender As Object, e As EventArgs) Handles Button20.Click
        Op = ""
        TextBox1.Clear()
    End Sub

    Private Sub Button25_Click(sender As Object, e As EventArgs) Handles Button25.Click
        Op = "+"
        num1 = Val(TextBox1.Text)
        TextBox1.Clear()
    End Sub

    Private Sub Button19_Click(sender As Object, e As EventArgs) Handles Button19.Click
        Op = "/"
        num1 = Val(TextBox1.Text)
        TextBox1.Clear()
    End Sub

    Private Sub Button24_Click(sender As Object, e As EventArgs) Handles Button24.Click
        Op = "x"
        num1 = Val(TextBox1.Text)
        TextBox1.Clear()
    End Sub

    Private Sub Button29_Click(sender As Object, e As EventArgs) Handles Button29.Click
        Op = "-"
        num1 = Val(TextBox1.Text)
        TextBox1.Clear()
    End Sub

    Private Sub Button32_Click(sender As Object, e As EventArgs) Handles Button32.Click
        Op = "%"
        num1 = Val(TextBox1.Text)
        TextBox1.Clear()
    End Sub

    Private Sub Button10_Click(sender As Object, e As EventArgs) Handles Button10.Click
        Op = "x^y"
        num1 = Val(TextBox1.Text)
        TextBox1.Clear()
    End Sub

    Private Sub Button33_Click(sender As Object, e As EventArgs) Handles Button33.Click

        If (Op <> "") Then
            num2 = Val(TextBox1.Text)
            Dim r As Double
            Select Case Op
                Case "+"
                    r = suma(num1, num2)
                Case "-"
                    r = resta(num1, num2)
                Case "/"
                    r = division(num1, num2)
                Case "x"
                    r = multiplicacion(num1, num2)
                Case "%"
                    r = fmod(num1, num2)
                Case "x^y"
                    r = exp(num1, num2)
            End Select
            Op = ""
            num1 = r
            TextBox1.Text = r
        End If

    End Sub

    Function suma(ByVal a As Double, ByVal b As Double)
        Return a + b
    End Function

    Function resta(ByVal a As Double, ByVal b As Double)
        Return a - b
    End Function

    Function division(ByVal a As Double, ByVal b As Double)
        Return a / b
    End Function

    Function multiplicacion(ByVal a As Double, ByVal b As Double)
        Return a * b
    End Function

    Function fmod(ByVal a As Double, ByVal b As Double)
        Return a Mod b
    End Function

    Function exp(ByVal a As Double, ByVal b As Double)
        Return a ^ b
    End Function


    Function exp1(ByVal a)
        Return a ^ -1
    End Function


    Function exp2(ByVal a)
        Return a ^ 2
    End Function

    Function exp3(ByVal a)
        Return a ^ 3
    End Function

    Private Sub Button5_Click(sender As Object, e As EventArgs) Handles Button5.Click
        TextBox1.Text = Math.Sin(TextBox1.Text)
        num1 = Val(TextBox1.Text)
    End Sub

    Private Sub Button6_Click(sender As Object, e As EventArgs) Handles Button6.Click
        TextBox1.Text = Math.Cos(TextBox1.Text)
        num1 = Val(TextBox1.Text)
    End Sub

    Private Sub Button7_Click(sender As Object, e As EventArgs) Handles Button7.Click
        TextBox1.Text = Math.Tan(TextBox1.Text)
        num1 = Val(TextBox1.Text)
    End Sub

    Private Sub Button1_Click(sender As Object, e As EventArgs) Handles Button1.Click
        TextBox1.Text = Math.Sinh(TextBox1.Text)
        num1 = Val(TextBox1.Text)
    End Sub

    Private Sub Button2_Click(sender As Object, e As EventArgs) Handles Button2.Click
        TextBox1.Text = Math.Cosh(TextBox1.Text)
        num1 = Val(TextBox1.Text)
    End Sub

    Private Sub Button3_Click(sender As Object, e As EventArgs) Handles Button3.Click
        TextBox1.Text = Math.Tanh(TextBox1.Text)
        num1 = Val(TextBox1.Text)
    End Sub

    Private Sub Button4_Click(sender As Object, e As EventArgs) Handles Button4.Click
        TextBox1.Text = Math.Sqrt(TextBox1.Text)
        num1 = Val(TextBox1.Text)
    End Sub

    Private Sub Button8_Click(sender As Object, e As EventArgs) Handles Button8.Click
        TextBox1.Text = exp3(TextBox1.Text)
        num1 = Val(TextBox1.Text)
    End Sub

    Private Sub Button11_Click(sender As Object, e As EventArgs) Handles Button11.Click
        TextBox1.Text = exp1(TextBox1.Text)
        num1 = Val(TextBox1.Text)
    End Sub

    Private Sub Button9_Click(sender As Object, e As EventArgs) Handles Button9.Click
        TextBox1.Text = exp2(TextBox1.Text)
        num1 = Val(TextBox1.Text)
    End Sub

    Private Sub Button12_Click(sender As Object, e As EventArgs) Handles Button12.Click
        TextBox1.Text = Math.Log(TextBox1.Text)
        num1 = Val(TextBox1.Text)
    End Sub

    Private Sub Button13_Click(sender As Object, e As EventArgs) Handles Button13.Click
        TextBox1.Text = Math.Log10(TextBox1.Text)
        num1 = Val(TextBox1.Text)
    End Sub

    Private Sub Button14_Click(sender As Object, e As EventArgs) Handles Button14.Click
        TextBox1.Text = Math.Abs(Val(TextBox1.Text))
        num1 = Val(TextBox1.Text)
    End Sub

    Private Sub Button15_Click(sender As Object, e As EventArgs) Handles Button15.Click
        TextBox1.Text = Math.Exp(TextBox1.Text)
        num1 = Val(TextBox1.Text)
    End Sub
End Class
